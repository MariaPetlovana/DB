cp Procfile_web Procfile
git add Procfile
git commit -m "Deploy web gui."
heroku git:remote -a laba-webserver
git push heroku master
