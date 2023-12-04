(ns mark-move.core)

;; ./bin notes/Foo.md notes/Bar.md
;; -> 10 Links updated
;; -> See log.txt
(defn move-files
  ([files dest])
  ([files dest base]))

(defn move-file [filename base-folder])

;; '({:id foo.md :out [...] :in [...]})
(defn make-node [id] {:id id})
(defn make-graph ([nodes])([] []))
(defn move-node [node graph])

(make-node "foo.md")
(make-graph)

(def file-system
  {"one.md" {:contents "# one.md\n[[two.md]]"}
   "two.md" {:contents "# two.md\n"}})

(get-in file-system ["one.md" :contents])

;; [
;;  [:file-rename :src "two.md" :dest "three.md"]}
;;  [:patch-element :src "one.md" :path "/link/3" :value "[[three.md]]"]}
;;  ]

;; [
;;  (file-rename {:src "two.md" :dest "three.md" })
;;  (patch-element {:src "one.md" :path "/link[3]" :value "[[three.md]]" })
;; ]




