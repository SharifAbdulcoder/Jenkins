node('master'){
  stage('Check terraform') {

    try {
      // Trying to run terraform command
      env.terraform  = sh returnStdout: true, script: 'terraform --version'
      echo """
      echo Terraform already installed version ${env.terraform}
      """

    } catch(er) {
      // if terraform does not installed in system stage will install the terraform
       stage('Installing Terraform') {
         sh """
         wget https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip
         yum install unzip -y
         unzip terraform_0.11.11_linux_amd64.zip
         mv terraform /bin
         """
       stage('Installing Helm') {
         sh """
         wget https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz
         tar -zxvf helm-v2.11.0-linux-amd64.tar.gz
         mv linux-amd64/helm /usr/local/bin/helm
         """

       }

       }
    }
  }
}
