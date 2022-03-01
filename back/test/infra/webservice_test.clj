(ns infra.webservice-test
  (:require [clojure.test :refer :all]
            [infra.webservice :as ws]
            [io.pedestal.http :as http]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn controla-estado-ambiente [f]
  (let [ws (ws/start-webservice-test)]
    (f)
    (http/stop ws)))

(use-fixtures :once controla-estado-ambiente)

(deftest test-call-api-result-true
  (let [result (client/get "http://localhost:8000/scramble?str1=rekqodlw&str=world")]
    (is (= {:code 0 :result true} (-> result :body (json/read-str :key-fn keyword)))))
  (let [result (client/get "http://localhost:8000/scramble?str1=cedewaraaossoqqyt&str=codewars")]
    (is (= {:code 0 :result true} (-> result :body (json/read-str :key-fn keyword))))))

(deftest test-call-api-result-false
  (let [result (client/get "http://localhost:8000/scramble?str1=katas&str2=steak")]
    (is (= {:code 0 :result false} (-> result :body (json/read-str :key-fn keyword))))))

(deftest test-call-api-result-true-with-not-permit-chars
  (let [result (client/get "http://localhost:8000/scramble?str1=cedewaraaos--ASDS12333...---.soqqyt&str=codew--ASDS12333...---.ars")]
    (is (= {:code 0 :result true} (-> result :body (json/read-str :key-fn keyword))))))