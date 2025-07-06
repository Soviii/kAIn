FROM postgres:15.5

WORKDIR /app

COPY ./scripts /app/scripts/
RUN chmod +x /app/scripts/*.sh

EXPOSE 5432