FROM node:alpine3.16

WORKDIR /webapp

COPY package.json /webapp/package.json

RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*

RUN npm install

COPY . /webapp/

CMD npm run start

EXPOSE 3000