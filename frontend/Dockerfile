FROM node:18.12-alpine3.16 as build
RUN mkdir -p /app
WORKDIR /app
COPY package*.json ./app
RUN npm install
COPY . /app
RUN npm run build --prod
FROM nginx:3.16.2-alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html
#/app/docs or /app/dist?
