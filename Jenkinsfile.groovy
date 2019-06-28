
node('master') {
  properties([parameters([booleanParam(defaultValue: false, description: '', name: 'Terraform_Apply')])])
  properties([parameters([booleanParam(defaultValue: false, description: '', name: 'Terraform_Destroy')])])
  properties([parameters([choice(choices: ['DEV', 'QA', 'STAGE', 'PROD'], description: 'please choose environment', name: 'ENV')])])


    stage('Clone SCM') {
    git 'https://github.com/SharifAbdulcoder/Jenkins.git'
  }

   stage("Terraform init") {
     dir("${WORKSPACE}/Jenkins/artemis_tf") {
       sh "terraform init"
     }
   }

   stage('Terraform Apply/Plan') {
         if (params.Terraform_Apply) {
           dir("${WORKSPACE}/Jenkins/artemis_tf/") {
             echo "##### Terraform Applying the Changes ####"
             sh "terraform apply  --auto-approve"
           }
        }

          else {
            dir("${WORKSPACE}/Jenkins/artemis_tf/"){
              sh "terraform plan"
            }
          }
    }
    stage('Terraform Destoy') {
          if (params.Terraform_Destroy) {
            dir("${WORKSPACE}/Jenkins/artemis_tf") {
              sh "terraform destroy  --auto-approve"
            }
          }
    }
}
