node('master') {
  properties([parameters([booleanParam(defaultValue: false, description: 'This will do terraform apply', name: 'Terraform_Apply'), booleanParam(defaultValue: false, description: 'This will do terraform destroy', name: 'Terraform_Destroy'), choice(choices: ['DEV', 'QA', 'STAGE', 'PROD'], description: 'Please select the ENV ', name: 'ENV')])])
    stage('Clone repo') {
    git 'https://github.com/SharifAbdulcoder/Jenkins.git'
  }

   stage("Terraform init") {
     dir("${WORKSPACE}/artemis_tf") {
       sh "terraform init"
     }
   }

   stage('Terraform Apply/Plan') {
         if (params.Terraform_Apply) {
           dir("${WORKSPACE}/artemis_tf") {
             echo "##### Terraform Applying the Changes ####"
             sh "terraform apply  --auto-approve"
           }
      }  else {
        dir("${WORKSPACE}/artemis_tf/") {
          sh "terraform plan"
      }
    }
  }

    stage('Terraform Destoy') {
         if (params.Terraform_Destroy) {
           dir("${WORKSPACE}/artemis_tf") {
             echo "##### Terraform Destroying the Changes ####"
             sh "terraform destroy  --auto-approve"
           }
         }
       }


}
