node{
    stage("Update jenkins"){
        properties([parameters([string(defaultValue: '18.205.109.200', description: 'Please provide IP', name: 'ENVIR', trim: true)])])
        sh "echo Parameter added"
    }
    stage("Install git"){
        sh "ssh  ec2-user@${ENVIR} sudo yum install python-pip git -y"
    }

    stage("Remove repo"){
       sh "ssh  ec2-user@${ENVIR} sudo  rm -rf /home/ec2-user/Flaskex"
   }

    stage("Pull Repo"){
        sh "ssh  ec2-user@${ENVIR} git clone https://github.com/anfederico/Flaskex.git"
    }
    stage("Install Requirements"){
        //sh "virtualenv /tmp/venv"
        //sh ". /tmp/venv/bin/activate"
        sh "echo Hello"
    }
    stage("Pip Install"){
        sh "ssh  ec2-user@${ENVIR} sudo pip install -r /home/ec2-user/Flaskex/requirements.txt"
    }
    stage("Run App"){
        sh "ssh  ec2-user@${ENVIR} sudo python /home/ec2-user/Flaskex/app.py"
    }
}
