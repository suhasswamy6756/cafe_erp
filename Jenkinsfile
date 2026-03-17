pipeline {
    agent any

    environment {
        AWS_REGION = "eu-north-1"
        ECR_REPO = "523874365958.dkr.ecr.eu-north-1.amazonaws.com/cafe-erp-backend"
        CLUSTER = "cafe-erp-cluster-test"
        SERVICE = "new-task-erp-java-backend-service-msa4ei96"
        TASK_DEF_NAME = "new-task-erp-java-backend"
        IMAGE_TAG = "${BUILD_NUMBER}"
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
                sh '''
                docker build -t cafe-erp-backend .
                docker tag cafe-erp-backend:latest $ECR_REPO:$IMAGE_TAG
                '''
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
                docker push $ECR_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Update Task Definition & Deploy') {
            steps {
                sh '''
                echo "Fetching current task definition..."

                TASK_DEF=$(aws ecs describe-task-definition \
                  --task-definition $TASK_DEF_NAME \
                  --query taskDefinition)

                echo "Updating image with new tag..."

                NEW_TASK_DEF=$(echo $TASK_DEF | jq --arg IMAGE "$ECR_REPO:$IMAGE_TAG" '{
                  family: .family,
                  containerDefinitions: (.containerDefinitions | map(.image = $IMAGE)),
                  executionRoleArn: .executionRoleArn,
                  networkMode: .networkMode,
                  requiresCompatibilities: .requiresCompatibilities,
                  cpu: .cpu,
                  memory: .memory
                }')

                echo $NEW_TASK_DEF > new-task-def.json

                echo "Registering new task definition revision..."

                aws ecs register-task-definition \
                  --cli-input-json file://new-task-def.json

                echo "Updating ECS service..."

                aws ecs update-service \
                  --cluster $CLUSTER \
                  --service $SERVICE \
                  --force-new-deployment
                '''
            }
        }
    }
}