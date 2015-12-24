cp Procfile_ws Procfile
git add Procfile
git commit -m "Deploy web-service server."
heroku git:remote -a laba-webservice
git push heroku master
