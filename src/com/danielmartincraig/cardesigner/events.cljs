(ns com.danielmartincraig.cardesigner.events
  (:require
   [re-frame.core :as re-frame]
   [com.danielmartincraig.cardesigner.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))

(re-frame/reg-event-db
 ::update-wheelbase
 (fn-traced [db [_ new-wheelbase]]
            (assoc db :wheelbase new-wheelbase)))
