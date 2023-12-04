(ns mark-move.cursor
  (:require [clojure.string :as str]))
(import (ai.serenade.treesitter TreeCursor))

(defn get-type [cursor]
  (-> cursor
      (.getCurrentNode)
      (.getType)))

;; Get the nth child or return nil
(defn goto-nth-sibling [cursor n]
  (if (zero? n)
    true
    (if (.gotoNextSibling cursor)
      (recur cursor (dec n))
      false)))

(defn move-to-nth-child [cursor i]
  (and 
   (.gotoFirstChild cursor)
   (goto-nth-sibling cursor i)
   cursor))

(defn move-to
  ([cursor path]
   (move-to
    cursor (rest path) (first path)))

  ([cursor path token]
   (if (nil? token) cursor
       (if (move-to-nth-child cursor token)
         (move-to cursor path)
         false))))

(defn to-str [cursor]
  (if (instance? TreeCursor cursor) 
    (-> cursor
        (.getCurrentNode)
        (.getNodeString))
    (str cursor)))


(defn get-range
  ([cursor]
   (let [node (.getCurrentNode cursor)
         start (.getStartByte node)
         end (.getEndByte node)]
     [start end])))

(defn get-value [cursor document]
  (let [[a b]  (get-range cursor)]
    (subs document a b)))


