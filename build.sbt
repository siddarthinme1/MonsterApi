name := """Monster"""
organization := "com.monster"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.12"




libraryDependencies ++= Seq(javaJdbc,
  jdbc,
  guice,
  javaJpa,
  "org.hibernate" % "hibernate-core" % "5.5.6",
  "org.hibernate" % "hibernate-entitymanager" % "5.6.15.Final",
  "com.typesafe.play" %% "play" % "2.8.18",
  "com.microsoft.sqlserver" % "mssql-jdbc" % "7.0.0.jre8",
  "com.typesafe.play" %% "play-java" % "2.8.18",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.2",
  "org.projectlombok" % "lombok" % "1.18.26",
  "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.2"

)
