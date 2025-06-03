FROM node:20.11.1

WORKDIR /app
COPY package*.json ./

# Only install dependencies; code comes from the volume mount
RUN npm install

EXPOSE 3000

