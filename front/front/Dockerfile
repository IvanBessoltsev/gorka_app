FROM node:alpine3.16

WORKDIR /webapp

COPY package.json /webapp/package.json

RUN npm install

COPY . /webapp/

CMD npm run start

EXPOSE 3000