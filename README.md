# Whitbread Sample &micro;Service

## Requirenments

All services should:
* include .gitignore
* have Dockerfile located at /src/main/docker/Dockerfile
* include information section in /src/main/resources/application.yml
```info:
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
           message: @git.commit.message.short@```