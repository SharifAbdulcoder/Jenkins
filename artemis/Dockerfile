FROM centos:7
COPY . /app
RUN yum -y install epel-release && yum clean all
RUN yum -y install python-pip && yum clean all
RUN yum install mariadb-devel  -y
RUN yum install gcc -y
RUN yum -y install python-devel libxslt-devel libffi-devel openssl-devel
WORKDIR /app
RUN pip install --upgrade pip enum34
RUN pip install -r requirements.txtdco
EXPOSE 80
CMD ["pip", "install", "--upgrade", "pip"]
CMD ["python", "app.py"]
