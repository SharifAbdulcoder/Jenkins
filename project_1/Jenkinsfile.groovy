node('master') {
  stage('Clone repo') {
    git 'https://github.com/SharifAbdulcoder/Jenkins.git'
  }

  stage('Check terraform') {
         try {
           // Trying to run terraform command
           env.terraform  = sh returnStdout: true, script: 'terraform --version'
           echo """
           echo terraform is already installed version ${env.terraform}
           """
           } catch(er) {
                 // if terraform does not installed in system stage will install the terraform
                  stage('Installing terraform') {
                    sh """
                    yum install terraform -y
                    """
                  }
               }
             }


  stage("Terraform plan"){
           dir("${WORKSPACE}/project_1/") {
           sh "terraform init"
           sh "terraform plan"
           }
         }
         stage("Terraform apply"){
           if (!params.terraform-destroy) {
               if (params.terraform-apply) {
                 dir("${WORKSPACE}/project_1/") {
                   echo "#### Terraform Applying the Changes #####"
                   sh "terraform apply --auto-approve"
                 }
               }
             }
           }
         }
