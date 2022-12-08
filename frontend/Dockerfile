FROM node:18.12-alpine3.16 as build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY frontend/package.json frontend/package-lock.json /workspace/
RUN npm install
COPY frontend/ /workspace
RUN npm run build --omit=dev
FROM nginx:1.23.2-alpine
COPY --from=build /workspace/dist/frontend /usr/share/nginx/html
