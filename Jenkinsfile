
node('master') {
  properties([parameters([string(defaultValue: 'DEV', description: 'Provide ENV', name: 'ENV', trim: true)])])

    stage('Checkout SCM') {
    git 'https://github.com/dilfuza97/Kubernetes_jenkins.git'
  }

   stage("Terraform init") {
     dir("${WORKSPACE}/Kubernetes_jenkins/") {
       sh "terraform init"
     }
   }


}
