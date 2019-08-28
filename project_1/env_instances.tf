provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "Dev" {
  ami                         = "ami-0b898040803850657"
  instance_type               = "t2.micro"
  key_name                    = "jenkins_key"
  subnet_id                   = "subnet-838061ce"
  associate_public_ip_address = "true"
}

resource "aws_instance" "QA" {
  ami                         = "ami-0b898040803850657"
  instance_type               = "t2.micro"
  key_name                    = "jenkins_key"
  subnet_id                   = "subnet-b55b50ba"
  associate_public_ip_address = "true"
}

resource "aws_instance" "PROD" {
  ami                         = "ami-0b898040803850657"
  instance_type               = "t2.micro"
  key_name                    = "jenkins_key"
  subnet_id                   = "subnet-cd257791"
  associate_public_ip_address = "true"
}
