# build
`mvn install`

# deploy as systemd service

/etc/systemd/system/reservationstest.service
```bash
[Unit]
Description=reservationstest
After=syslog.target

[Service]
User=reservationsservice
ExecStart=/usr/bin/java -jar /path/to/reservations-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target

```
```bash
sudo systemctl enable reservationstest.service
sudo systemctl start reservationstest.service 
```

