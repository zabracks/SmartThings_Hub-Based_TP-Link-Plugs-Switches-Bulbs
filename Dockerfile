FROM node:10.2.1-alpine

WORKDIR /opt/tplink-srv
ADD ./TP-LinkHub_v2.js .

EXPOSE 8082

CMD node --version && node TP-LinkHub_v2.js