# Whitbread Sample &micro;Service

## Requirenments

All services should:
* include .gitignore
* have Dockerfile located at /src/main/docker/Dockerfile
* include information section in /src/main/resources/application.yml
```
info:
     build:
       groupId: @project.groupId@
       artifact: @project.artifactId@
       description: @project.description@
       version: @project.version@
       git:
         branch: @git.branch@
         commit:
           id: @git.commit.id@
           time: @git.commit.time@
           message: @git.commit.message.short@
```
* have its own package, ex. uk.co.whitbread.sample
Refs: 
1. [A New Way to Do Continuous Delivery with Maven and Jenkins Pipeline](https://www.cloudbees.com/blog/new-way-do-continuous-delivery-maven-and-jenkins-pipeline)
