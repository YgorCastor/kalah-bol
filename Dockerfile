FROM oracle/graalvm-ce:19.0.0 as graalvm
COPY . /home/app/kalah-bol
WORKDIR /home/app/kalah-bol
RUN gu install native-image
RUN native-image --no-server -cp build/libs/kalah-bol-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/kalah-bol .
ENTRYPOINT ["./kalah-bol"]