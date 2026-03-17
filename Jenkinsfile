pipeline {
    agent any

    environment {
        AWS_REGION = "eu-north-1"
        ECR_REPO = "523874365958.dkr.ecr.eu-north-1.amazonaws.com/cafe-erp-backend"
        CLUSTER = "cafe-erp-cluster-test"
        SERVICE = "new-task-erp-java-backend-service-msa4ei96"
    }

    stages {

        stage('Clone Code') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t cafe-erp-backend .'
            }
        }

        stage('Login to AWS ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region $AWS_REGION \
                | docker login --username AWS --password-stdin $ECR_REPO
                '''
            }
        }

        stage('Push Image to ECR') {
            steps {
                sh '''
                docker tag cafe-erp-backend:latest $ECR_REPO:latest
                docker push $ECR_REPO:latest
                '''
            }
        }

        stage('Deploy to ECS') {
            steps {
                sh '''
                aws ecs update-service \
                  --cluster $CLUSTER \
                  --service $SERVICE \
                  --force-new-deployment
                '''
            }
        }
    }
}