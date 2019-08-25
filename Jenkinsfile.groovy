pipeline {
  agent any
  stages {
    stage('Run Command') {
      steps {
        sh '''
        set +xe
        echo "Hello, ... its mee"
        sudo yum install httpd -y
        ping -c 4 google.com
        echo "My Jenknins worksss > /var/www/html/index.html"
        '''
      }
    }
  }
}
