node('master') {
  stage('Clone repo') {
    git 'https://github.com/SharifAbdulcoder/Resume_app.git'
  }

  stage('Check terraform') {
         try {
           // Trying to run terraform command
           env.terraform  = sh returnStdout: true, script: 'terraform --version'
           echo """
           echo AWS CLI is already installed version ${env.terraform}
           """
           } catch(er) {
                 // if terraform does not installed in system stage will install the terraform
                  stage('Installing AWS CLI') {
                    sh """
                    yum install terraform -y
                    """
                  }
               }
             }


  stage("Terraform plan"){
           dir("${WORKSPACE}/Containerized-Python_app/") {
           sh "terraform init"
           sh "terraform plan"
           }
         }
         stage("Terraform apply"){
           if (!params.terraform-destroy) {
               if (params.terraform-apply) {
                 dir("${WORKSPACE}/Containerized-Python_app/") {
                   echo "#### Terraform Applying the Changes #####"
                   sh "terraform apply --auto-approve"
                 }
               }
             }
           }
         }
