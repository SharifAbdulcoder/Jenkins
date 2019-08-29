node{
    stage("Update jenkins"){
        properties([parameters([string(defaultValue: '18.205.109.200', description: 'Please provide IP', name: 'ENVIR', trim: true)])])
        sh "echo Parameter added"
    }
    stage("Install git"){
        sh "ssh  ec2-user@${ENVIR} sudo yum install git python-pip3 -y"
    }
    stage("Pull Repo"){
      try {
         // Trying to run terraform command
         env.repo  = sh returnStdout: true, script: 'ls  home/ec2-user/Resume_app'
         echo """
         echo repo exists  ${env.terraform}
         """
         } catch(er) {
               // if terraform does not installed in system stage will install the terraform
                stage('pull repo') {
                  sh """
                  sh "ssh  ec2-user@${ENVIR} git clone https://github.com/SharifAbdulcoder/Resume_app.git"
                  """
                }
             }
           }

    stage("Install Requirements"){
        //sh "virtualenv /tmp/venv"
        //sh ". /tmp/venv/bin/activate"
        sh "echo Hello"
    }
    stage("Pip Install"){
        sh "ssh  ec2-user@${ENVIR} pip install -r /home/ec2-user/Resume_app/requirements.txt"
    }
    stage("Run App"){
        sh "ssh  ec2-user@${ENVIR}  python3 /home/ec2-user/Resume_app/simple.py"
    }
}
