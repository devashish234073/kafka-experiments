# kafka-experiments

This is a starter project for kafka just lauch the template.yml in cloudformation and it will set up the instances necessary.

Once deployed the cft's output will have details like this:

<img width="1582" height="694" alt="image" src="https://github.com/user-attachments/assets/7907def7-c922-4cba-843e-d7ed857761a4" />

THe producer url will take you to a UI like this:

<img width="775" height="332" alt="image" src="https://github.com/user-attachments/assets/c9b9a5da-c18d-4980-8bce-4f81f8ed3eb8" />

And the consumer url to this:

<img width="700" height="385" alt="image" src="https://github.com/user-attachments/assets/b2e42203-a3fd-4638-ac10-81db22ccf264" />

Even for the three broker endpoints links are there that is for http server running on port 8080 for the /var/log of the ec2 instance
that is handy for debugging startup logs without doing ssh to instance:

<img width="689" height="235" alt="image" src="https://github.com/user-attachments/assets/aa72e47a-9f63-4ac7-ad76-99090b219b4b" />

This is how it looks for one of the broker:

<img width="917" height="820" alt="image" src="https://github.com/user-attachments/assets/a7faf640-f882-4dd5-8efb-3ae83dfc066d" />

Update: 6th Dec 2025:

Added ClusterController to get cluster status:

<img width="1602" height="964" alt="image" src="https://github.com/user-attachments/assets/2f673e8c-2a51-4536-ae94-65f872b41865" />

It's using Kafka admin:

<img width="1036" height="727" alt="image" src="https://github.com/user-attachments/assets/4b063a06-643d-4cb5-ad13-6f7fcf0b4757" />

It automatcally gets the broker details from spring.kafka.bootstrap-servers property in application.properties






