(ns mark-move.playground
  (:require [mark-move.markdown :as m]
            [mark-move.cursor :as c]))

;; (document
;;  (tight_list
;;   (list_item (list_marker) (paragraph (text)))
;;   (list_item (list_marker) (paragraph (text))))
;;  (paragraph (text)))
;; (m/pprint doc-list)
(def doc-list "- one\n\n\n\n- two\n\nthree")
(m/replace-in doc-list [1 0] "hello")
(m/pprint doc-list)
;; => "(document\n (loose_list\n  (list_item (list_marker) (paragraph (text)))\n  (list_item (list_marker) (paragraph (text))))\n (paragraph (text)))\n"

(with-open [cursor (m/cursor-for doc-list)] 
  (-> cursor
      (c/move-to [0 1])
      (#(if-not % (throw (Exception. "Not found")) %))
      (c/get-value doc-list)
      (c/to-str)))

;; [0 1 1 1 0]
;; Can be made into a pretty link...
;; /document/tight_list/list_item:1/paragraph/text
;; Which is equivalent to
;; /document:0/tight_list:0/list_item:1/paragraph:0/text:0
;; Which will find the nth element of that type.
;; This only serves as an extra validation of the path. A mild checksum
;; This could become CSS with
;; tight_list > :nth-child(2) text 

