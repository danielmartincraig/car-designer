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
     :label (str "Hello from " @name ". This is the Home Page.")
     :level :level1
     :class (styles/level1)]))

(defn car-view []
  (let [wheelbase (re-frame/subscribe [::subs/wheelbase])]
    [:svg {:width 400 :height 100}
     [:circle {:cx "50" :cy "50" :r "40" :stroke "black" :stroke-width "4" :fill "khaki"}]
     [:circle {:cx (str (+ 50 @wheelbase)) :cy "50" :r "40" :stroke "black" :stroke-width "4" :fill "khaki"}]]))

(defn car-customizer-view []
  (let [wheelbase (re-frame/subscribe [::subs/wheelbase])]
    [re-com/slider
     :model wheelbase
     :on-change #(re-frame/dispatch [::events/update-wheelbase %])
     :max 300]))

(defn home-body []
  [re-com/v-box
   :src    (at)
   :gap    "1em"
   :children [[car-view]
              [car-customizer-view]]])

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
              [link-to-home-page]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [(routes/panels @active-panel)]]))
