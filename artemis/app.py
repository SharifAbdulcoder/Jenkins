from flask_login import LoginManager, UserMixin, login_user, login_required, logout_user, current_user
from werkzeug.security import generate_password_hash, check_password_hash
from flask import Flask, render_template, redirect, url_for, request, jsonify
from wtforms import StringField, PasswordField, BooleanField, TextField
from wtforms.validators import InputRequired, Email, Length
from flask_bootstrap import Bootstrap
from flask_admin.contrib.sqla import ModelView
from flask_wtf import FlaskForm, RecaptchaField
from flask_sqlalchemy  import SQLAlchemy
from flask_admin import Admin, AdminIndexView
import subprocess
import smtplib
app = Flask(__name__)
bootstrap = Bootstrap(app)

# Set up Main config
app.config['SECRET_KEY'] = 'Thisissupposedtobesecret!'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)


login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'login'

@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))



class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    firstname = db.Column(db.String(15))
    lastname = db.Column(db.String(15))
    username = db.Column(db.String(15), unique=True)
    email = db.Column(db.String(50), unique=True)
    password = db.Column(db.String(80))
    status = db.Column(db.String(5))
    role = db.Column(db.String(5))


class RegisterForm(FlaskForm):
    firstname = StringField('Firstname', validators=[InputRequired(), Length(min=4, max=15)])
    lastname = StringField('Lastname', validators=[InputRequired(), Length(min=4, max=15)])
    email = StringField('email', validators=[InputRequired(), Email(message='Invalid email'), Length(max=50)])
    username = StringField('username', validators=[InputRequired(), Length(min=4, max=15)])
    password = PasswordField('password', validators=[InputRequired(), Length(min=8, max=80)])


@app.route('/', methods=['GET', 'POST'])
def index():
    if 'username' in request.form:
        username = request.form['username']
        password = request.form['password']
        user = User.query.filter_by(username=username).first()
        if user:
            if check_password_hash(user.password, password):
                login_user(user)
                return redirect(url_for('dashboard'))
        return '<h1>Invalid username or password</h1>'
    return render_template('index.html')

@app.route('/signup', methods=['GET', 'POST'])
def signup():
    form = RegisterForm()
    if form.validate_on_submit():
        user = User.query.filter_by(username=form.username.data).first()
        hashed_password = generate_password_hash(form.password.data, method='sha256')
        new_user = User(username=form.username.data, firstname=form.firstname.data, lastname=form.lastname.data,  email=form.email.data, password=hashed_password, status='False')
        if user:
            if user.username == form.username.data:
                return '<h1>This user name is exist</h1>'
        db.session.add(new_user)
        db.session.commit()
        return redirect(url_for('login'))
    return render_template('signup.html', form=form)

@app.route('/contact', methods=['GET', 'POST'])
def contact():
    return render_template('contact.html')


@app.route('/dashboard', methods=['GET', 'POST'])
def dashboard():
    return render_template('dashboard.html')

if __name__ == '__main__':
    app.run(debug=True, port=5000, host='0.0.0.0')
