# deploy using ./deploy.sh

mvn clean package
sudo cp target/api.war /var/lib/tomcat9/webapps/
