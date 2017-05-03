(ns ^{:hoplon/page "index.html"} pages.index
  (:require [clojure.pprint :as pp]
            [hoplon.core    :as h :refer [a em div html head title body h2 fieldset label input pre for-tpl]]
            [hoplon.jquery]
            [javelin.core   :as j :refer [cell cell=]]
            [linked.core    :as linked]))

(defn retain
  "Removes elements from the front of linked set s until n or fewer remain."
  [n s]
  (if (> (count s) n)
    (recur n (disj s (first s)))
    s))

(defmethod h/on! :check
  [elem _ callback]
  (elem :change #(if (.. % -target -checked) (callback %))))

(defmethod h/on! :uncheck
  [elem _ callback]
  (elem :change #(if-not (.. % -target -checked) (callback %))))

(html
  (head
    (title "Constrained checkboxes in Hoplon"))
  (body
    (let [choices     ["Apple" "Apricot" "Banana" "Mango" "Orange" "Plum"]
          max-choices 2
          limit       (partial retain max-choices)
          chosen      (cell (linked/set))]
      (div
        (h2 (pp/cl-format nil "Which fruits do you want? Pick ~R or fewer." max-choices))
        (fieldset {:css {:border "none"}}
          (for [choice choices]
            (label :css {:display "block"}
              (input {:type    "checkbox"
                      :check   #(swap! chosen (comp limit conj) choice)
                      :uncheck #(swap! chosen (comp limit disj) choice)
                      :checked (cell= (contains? chosen choice))})
              choice)))
        (h2 "Chosen")
        (pre (cell= (pp/write chosen :stream nil :pretty true)))
        (a :href "https://github.com/alandipert/constrained-checkboxes-hoplon/"
          (em "Source code"))))))
