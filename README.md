# aws-fargate-cicd-demo

// pipeline{
    agent any
    tools {
    maven "maven"
    }
        stages {
   // stage('Clone From Repo'){
         steps {
       //      script {
   //   git credentialsId: 'GitHub-Credentials', url: 'https://github.com/arxy1/cicd-container.git'
    }
         }
    }
    //stage( 'Mvn package'){
     //    steps {
     //        script {
      //   def mvnHome = tool name: 'maven', type: 'maven'
      //   sh:'${mvnHome}/bin/mvn clean package -DskipTests'
      //   sh 'mvn clean install' 
//}
//}
//}
       
stage( 'Docker Build'){
     steps {
         script {
         def dockerHome = tool name: 'docker', type: 'dockerTool'
         sh:'${dockerHome}/bin/docker buildx build --platform=linux/amd64 -t auto:latest .'
}
}
     }
     stage('Push image to ECR'){
         steps{
         script {
             def dockerHome = tool name: 'docker', type: 'dockerTool'
         }   
         script {
             withCredentials([string(credentialsId: 'Access_key', variable: 'ACCESS_KEY')]){
    sh: '/opt/homebrew/bin/ aws configure set aws_access_key_id ${ACCESS_KEY}'
}
          
         script {
            withCredentials([string(credentialsId: 'ACCESS_KEY', variable: 'SECRET_KEY')]){
        sh: '/opt/homebrew/bin/aws configure set aws_secret_access_key ${SECRET_KEY}'
                
                sh: '/opt/homebrew/bin/ aws configure set region us-east-1'
                
                sh: '/opt/homebrew/bin/aws aws ecr get-login-password --region us-east-1 | ${dockerHome}/bin/docker login --username AWS --password-stdin 548020302753.dkr.ecr.us-east-1.amazonaws.com'
            
                sh: '${dockerHome}/bin/docker buildx build --platform=linux/amd64 -t nodeapp1:latest'
                
                sh: '${dockerHome}/bin/docker tag nodeapp1:latest 548020302753.dkr.ecr.us-east-1.amazonaws.com/auto:latest'
             
                sh: '${dockerHome}/bin/docker push 548020302753.dkr.ecr.us-east-1.amazonaws.com/auto:latest'
             
                sh '/opt/homebrew/bin/aws ecs update-service --cluster myClustev2 --service 03-service --task-definition task03:3 --desired-count 1 --force-new-deployment'
}
}
}
         }           
         }
     }
        }
