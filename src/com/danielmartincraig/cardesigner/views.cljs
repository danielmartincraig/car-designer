(ns com.danielmartincraig.cardesigner.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [com.danielmartincraig.cardesigner.styles :as styles]
   [com.danielmartincraig.cardesigner.events :as events]
   [com.danielmartincraig.cardesigner.routes :as routes]
   [com.danielmartincraig.cardesigner.subs :as subs]))



;; home

(defn home-title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :src   (at)
     :label (str "Car Designer")
     :level :level1
     :class (styles/level1)]))

(defn car-visualizer-view [car-id]
  (let [car (re-frame/subscribe [::subs/car car-id])]
    [:svg {:viewBox "-50 -50 300 150"}
     [:rect {:x 0 :y 0 :width (:body-length @car) :height 70 :style {:fill "red" :stroke-width 3 :stroke "black"}}]
     [:circle {:cx (:wheel-shift @car) :cy 70 :r (:wheel-radius @car) :stroke "black" :stroke-width "4" :fill "khaki"}]
     [:circle {:cx (+ (:wheel-shift @car) (:wheelbase @car)) :cy 70 :r (:wheel-radius @car) :stroke "black" :stroke-width "4" :fill "khaki"}]]))

(defn car-customizer-view [car-id]
  (let [car (re-frame/subscribe [::subs/car car-id])]
    [re-com/v-box
     :src    (at)
     :gap    "1em"
     :children [[re-com/slider
                 :model (:wheel-radius @car)
                 :on-change #(re-frame/dispatch [::events/update-car car-id :wheel-radius %])
                 :max (/ (:wheelbase @car) 2)]
                [re-com/slider
                 :model (:wheelbase @car)
                 :on-change #(re-frame/dispatch [::events/update-car car-id :wheelbase %])
                 :max (- (:body-length @car) (:wheel-shift @car))
                 :min (* 2 (:wheel-radius @car))]
                [re-com/slider
                 :model (:body-length @car)
                 :on-change #(re-frame/dispatch [::events/update-car car-id :body-length %])
                 :max 200
                 :min (+ (:wheelbase @car) (:wheel-shift @car))]
                [re-com/slider
                 :model (:wheel-shift @car)
                 :on-change #(re-frame/dispatch [::events/update-car car-id :wheel-shift %])
                 :max (- (:body-length @car) (:wheelbase @car))]]]))

(defn car-view [car-id]
  [re-com/v-box
   :src    (at)
   :gap    "1em"
   :children [[car-visualizer-view car-id]
              [car-customizer-view car-id]]])

(defn home-body []
  [re-com/h-box
   :src    (at)
   :gap    "1em"
   :children [[car-view 0]
              [car-view 1]]])

(defn link-to-about-page []
  [re-com/hyperlink
   :src      (at)
   :label    "go to About Page"
   :on-click #(re-frame/dispatch [::events/navigate :about])])

(defn home-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[home-title]
              [home-body]
              [link-to-about-page]]])


(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-title []
  [re-com/title
   :src   (at)
   :label "This is the About Page."
   :level :level1])

(defn about-body []
  [re-com/p "This is the car designer, it is a very cool way to build two rectangular red cars"])

(defn link-to-home-page []
  [re-com/hyperlink
   :src      (at)
   :label    "go to Home Page"
   :on-click #(re-frame/dispatch [::events/navigate :home])])

(defn about-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[about-title]
              [about-body]
              [link-to-home-page]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [(routes/panels @active-panel)]]))
