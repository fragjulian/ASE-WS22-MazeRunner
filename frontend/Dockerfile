FROM node:18.12-alpine3.16 as build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY package.json package-lock.json /workspace/
RUN npm install
COPY . /workspace
RUN npm run build --omit=dev
FROM nginx:1.23.2-alpine
COPY --from=build /workspace/dist/frontend /usr/share/nginx/html
