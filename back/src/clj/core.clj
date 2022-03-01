(ns core
  (:require [infra.webservice :as ws])
  (:gen-class))

(defn -main []
  (ws/start-webservice))