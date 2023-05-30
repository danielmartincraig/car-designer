(ns com.danielmartincraig.cardesigner.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub ::cars :-> :cars)

(re-frame/reg-sub
 ::car
 :<- [::cars]
 (fn [cars [_ n]]
   (nth cars n)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 ::wheelbase
 (fn [db]
   (:wheelbase db)))

(re-frame/reg-sub
 ::wheel-radius
 (fn [db]
   (:wheel-radius db)))

(re-frame/reg-sub
 ::body-length
 (fn [db]
   (:body-length db)))
