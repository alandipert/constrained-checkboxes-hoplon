(ns ^{:hoplon/page "index.html"} pages.index
  (:require [clojure.pprint :as pp]
            [hoplon.core    :as h :refer [div html head title body h2 fieldset label input for-tpl]]
            [hoplon.jquery]
            [javelin.core   :as j :refer [cell cell=]]
            [linked.core    :as linked]))

(defn choose
  "Adds x to the linked set ls, ensuring that ls only retains the latest n
  elements chosen so far."
  [ls n x]
  (if (> (count ls) (dec n))
    (recur (disj ls (first ls)) n x)
    (conj ls x)))

(html
  (head
    (title "Constrained checkboxes in Hoplon"))
  (body
    (let [choices     ["Apple" "Apricot" "Banana" "Mango" "Orange" "Plum"]
          max-choices 2
          chosen      (cell (linked/set))]
      (cell= (println chosen))
      (div
        (h2 (pp/cl-format nil "Which fruits do you want? Pick ~R or fewer." max-choices))
        (fieldset {:css {:border "none"}}
          (for [choice choices]
            (label :css {:display "block"}
              (input {:type    "checkbox"
                      :checked (cell= (contains? chosen choice))
                      :change  #(if (.. % -target -checked)
                                  (swap! chosen choose max-choices choice)
                                  (swap! chosen disj choice))})
              choice)))))))
