(ns infra.webservice
  (:require [clojure.java.io :as io]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.ring-middlewares :as middlewares]
            [app.scramble :as scramble]
            [clojure.data.json :as json]))

(defn index [_]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp (io/resource "public/index.html"))})

(defn scramble [{:keys [params]}]
  (let [{:keys [str1 str2]} params]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str {:code 0
                            :result (scramble/scramble? str1 str2)})}))

(def routes
  (route/expand-routes
   [[["/" {:get `index}]
     ["/scramble" ^:interceptors [(body-params/body-params)
                                  middlewares/params
                                  middlewares/keyword-params]
      {:get `scramble}]]]))

(defn start-webservice []
  (-> {:env :dev
       ::http/join? true
       ::http/routes routes
       ::http/resource-path  "/public"
       ::http/port   8000
       ::http/type   :jetty
       ::http/allowed-origins {:creds true :allowed-origins (constantly true)}}
      http/create-server
      http/start))

(defn start-webservice-test []
  (-> {:env :dev
       ::http/join?  false
       ::http/routes routes
       ::http/resource-path  "/public"
       ::http/port   8000
       ::http/type   :jetty
       ::http/allowed-origins {:creds true :allowed-origins (constantly true)}}
      http/create-server
      http/start))