node{
    stage("Update jenkins"){
        properties([parameters([string(defaultValue: '18.205.109.200', description: 'Please provide IP', name: 'ENVIR', trim: true)])])
        sh "echo Parameter added"
    }
    stage("Install git"){
        sh "ssh  ec2-user@${ENVIR} sudo yum install python-pip python3.7 python37 git -y"
    }

    stage("Remove repo"){
       sh "ssh  ec2-user@${ENVIR} sudo  rm -rf /home/ec2-user/Resume_app"
   }

    stage("Pull Repo"){
        sh "ssh  ec2-user@${ENVIR} git clone https://github.com/SharifAbdulcoder/Resume_app.git"
    }
    stage("Install Requirements"){
        //sh "virtualenv /tmp/venv"
        //sh ". /tmp/venv/bin/activate"
        sh "echo Hello"
    }
    stage("Pip Install"){
        sh "ssh  ec2-user@${ENVIR} sudo pip install -r /home/ec2-user/Resume_app/requirements.txt"
    }
    stage("Run App"){
        sh "ssh  ec2-user@${ENVIR} sudo python3 /home/ec2-user/Resume_app/sample.py"
    }
}
