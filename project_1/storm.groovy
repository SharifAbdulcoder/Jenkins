node{
    stage("Update jenkins"){
        properties([parameters([string(defaultValue: '18.205.109.200', description: 'Please provide IP', name: 'ENVIR', trim: true)])])
        sh "echo Parameter added"
    }
    stage("Install git"){
        sh "ssh  ec2-user@${ENVIR} sudo yum install python-pip git -y"
    }

    stage("Remove repo"){
       sh "ssh  ec2-user@${ENVIR} sudo  rm -rf /home/ec2-user/stormpath-flask-sample"
   }

   stage("Remove PID"){
        sh "ssh  ec2-user@${ENVIR} sudo kill -9 "${(pidof python)}""
    }

    stage("Pull Repo"){
        sh "ssh  ec2-user@${ENVIR} git clone https://github.com/SharifAbdulcoder/stormpath-flask-sample.git"
    }
    stage("Pip Install"){
        sh "ssh  ec2-user@${ENVIR} sudo pip install -r /home/ec2-user/stormpath-flask-sample/requirements.txt"
    }
    stage("Run App"){
        sh "ssh  ec2-user@${ENVIR} sudo python /home/ec2-user/stormpath-flask-sample/app.py"
    }
}
