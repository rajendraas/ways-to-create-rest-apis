FROM node:20.11-alpine3.18
WORKDIR /express-app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD [ "node", "src/app.js" ]
