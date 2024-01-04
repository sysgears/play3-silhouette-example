-- Update play_silhouette.users

-- !Ups
ALTER TABLE play_silhouette.users ADD "dateOfBirth" DATE NOT NULL;
ALTER TABLE play_silhouette.users ADD "dateOfCreation" TIMESTAMP NOT NULL;

-- !Downs
ALTER TABLE play_silhouette.users DROP "dateOfBirth";
ALTER TABLE play_silhouette.users DROP "dateOfCreation";