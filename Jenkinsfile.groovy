pipeline {
  agent any
  stages {
    stage('Run Command') {
      steps {
        sh '''
        set +xe
        ech "Hello, ... its mee"
        sudo yum install httpd -y
        ping -c 4 google.com
        echo "My Jenknins worksss > /var/www/html/index.html"
        '''
      }
    }
  }


  stage('Download Terraform') {
    steps {
      ws("/tmp")
      sh '''
      pwd
      '''
    }

  }
}
