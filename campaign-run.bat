START cmd.exe /k "cd campaignuploadspec & call mvn clean install & cd .. & cd campaignupload-service & call mvn clean install & call mvn spring-boot:run -Dspring-boot.run.profiles=prod"
