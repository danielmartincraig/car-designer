(ns com.danielmartincraig.cardesigner.db)

(def default-db
  {:name "re-frame"
   :active-panel :home-panel
   :cars [{:wheelbase 70
           :wheel-radius 10
           :body-length 140
           :wheel-shift 40}
          {:wheelbase 120
           :wheel-radius 20
           :body-length 160
           :wheel-shift 30}]})
