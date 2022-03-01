(ns front.core
    (:require
     [reagent.core :as r]
     [reagent.dom :as d]
     [ajax.core :refer [GET]]))

(defn handler [response result]
  (reset! result (:result response)))

(defn error-handler [{:keys [status status-text]} result]
  (reset! result (str "Something wrong for calling http://localhost:8000/scramble : \r" status " " status-text)))

(defn call-scrambler-api [str1 str2 result]
  (GET "http://localhost:8000/scramble" {:params {:str1 @str1
                                                  :str2 @str2}
                                         :handler (fn [e] (handler e result))
                                         :error-handler (fn [e] (error-handler e result))
                                         :response-format :json
                                         :keywords? true}))

(defn startup-page []
  (let [str1 (r/atom "")
        str2 (r/atom "")
        result (r/atom "")]
    (fn []
      [:div [:h4 "String Scrambler Project Testing"]
       [:div.inputWrapper
        [:label "Please insert String 1"]
        [:input {:value @str1
                 :on-change (fn [e] (reset! str1 (-> e .-target .-value)))}]]
       [:div.inputWrapper
        [:label "Please insert String 2"]
        [:input {:value @str2
                 :on-change (fn [e] (reset! str2 (-> e .-target .-value)))}]]
       [:div.inputWrapper
        [:div "scrambler result = " (str @result)]]
       [:div
        [:button {:on-click (fn [_] (call-scrambler-api str1 str2 result))} "Press to Scramble"]]])))

(defn mount-root []
  (d/render [startup-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
