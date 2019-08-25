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

  stages {
    stage('Install Terraform') {
      steps {
        ws("tmp/")
        sh '''
        set +xe
        wget https://releases.hashicorp.com/terraform/0.12.7/terraform_0.12.7_linux_amd64.zip
        unzip terraform_0.12.7_linux_amd64
        sudo mv terraform /bin/
        terraform --version
        '''
      }
    }
  }
}
