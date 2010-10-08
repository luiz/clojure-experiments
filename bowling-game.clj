(defn verifica [x y] (do (print x "==" y "? ") (assert (= x y)) (println "OK")))

(defn soma-dois-primeiros [frame] (reduce + (take 2 frame)))

(defn pontos-do-ultimo-frame [frame] (reduce + frame))

(defn segunda-jogada [jogo]
  (if (and (= 10 (ffirst jogo)) (> (count jogo) 1))
    (first (second jogo))
    (second (first jogo))))

(defn pontos-do-frame [atual resto-do-jogo]
  (if (empty? resto-do-jogo)
    (pontos-do-ultimo-frame atual)
    (+
      (soma-dois-primeiros atual)
      (if (= 10 (first atual))
        (segunda-jogada resto-do-jogo)
        0)
      (if (= 10 (soma-dois-primeiros atual))
        (first (first resto-do-jogo))
        0))))

(defn pontos2 [jogo]
  (if (empty? jogo)
    0
    (+
      (pontos-do-frame (first jogo) (rest jogo))
      (pontos2 (rest jogo)))))

(defn separa-frames [jogo]
  (if (empty? jogo)
    []
    (if (>= 3 (count jogo))
      (if (> 3 (count jogo))
        [[(first jogo) (second jogo) 0]]
        [jogo])
      (if (= 10 (first jogo))
        (concat [[(first jogo) 0]] (separa-frames (next jogo)))
        (concat [[(first jogo) (second jogo)]] (separa-frames (nnext jogo)))))))

(defn pontos [jogo]
  (pontos2 (separa-frames jogo)))

(def jogo-vazio (repeat 20 0))
(def jogo-de-uns (repeat 20 1))
(def jogo-com-um-spare (concat [5 5] (repeat 18 3)))
(def jogo-com-um-strike (concat [10] (repeat 18 3)))
(def jogo-com-dois-spares-seguidos (concat [5 5 5 5] (repeat 16 3)))
(def jogo-com-dois-strikes-seguidos (concat [10 10] (repeat 16 3)))
(def jogo-com-spare-no-ultimo-frame (concat (repeat 18 3) [5 5 5]))
(def jogo-com-strike-no-primeiro-turn-do-ultimo-frame (concat (repeat 18 3) [10 5 5]))
(def jogo-quase-perfeito (concat (repeat 9 10) [0 0]))
(def jogo-perfeito (repeat 12 10))

(verifica (concat (repeat 9 [0 0]) [[0 0 0]]) (separa-frames jogo-vazio))
(verifica (concat (repeat 9 [1 1]) [[1 1 0]]) (separa-frames jogo-de-uns))
(verifica (concat [[10 0]] (repeat 8 [3 3]) [[3 3 0]]) (separa-frames jogo-com-um-strike))
(verifica (concat [[10 0] [10 0]] (repeat 7 [3 3]) [[3 3 0]]) (separa-frames jogo-com-dois-strikes-seguidos))
(verifica (concat (repeat 9 [3 3]) [[10 5 5]]) (separa-frames jogo-com-strike-no-primeiro-turn-do-ultimo-frame))
(verifica (concat (repeat 9 [10 0]) [[10 10 10]]) (separa-frames jogo-perfeito))

(verifica 10 (soma-dois-primeiros [5 5 5]))
(verifica 15 (soma-dois-primeiros [10 5 5]))

(verifica 5 (pontos-do-ultimo-frame [2 3 0]))
(verifica 15 (pontos-do-ultimo-frame [5 5 5]))
(verifica 20 (pontos-do-ultimo-frame [10 5 5]))
(verifica 25 (pontos-do-ultimo-frame [10 10 5]))
(verifica 30 (pontos-do-ultimo-frame [10 10 10]))

(verifica 5 (segunda-jogada [[3 5]]))
(verifica 0 (segunda-jogada [[8 0]]))
(verifica 10 (segunda-jogada [[0 10]]))
(verifica 3 (segunda-jogada [[10 0] [3 0]]))
(verifica 5 (segunda-jogada [[10 5 1]]))
(verifica 10 (segunda-jogada [[10 0] [10 0 10]]))

(verifica 5 (pontos-do-frame [2 3] [[]]))
(verifica 5 (pontos-do-frame [2 3] [[5 5]]))
(verifica 15 (pontos-do-frame [5 5] [[5 5]]))
(verifica 20 (pontos-do-frame [10 0] [[5 5]]))
(verifica 30 (pontos-do-frame [10 0] [[10 10 10]]))
(verifica 20 (pontos-do-frame [10 0] [[10 0 10]]))
(verifica 28 (pontos-do-frame [10 0] [[10 0] [8 0]]))
(verifica 30 (pontos-do-frame [10 0] [[10 0] [10 0]]))

(verifica 0 (pontos jogo-vazio))
(verifica 20 (pontos jogo-de-uns))
(verifica 67 (pontos jogo-com-um-spare))
(verifica 70 (pontos jogo-com-um-strike))
(verifica 76 (pontos jogo-com-dois-spares-seguidos))
(verifica 87 (pontos jogo-com-dois-strikes-seguidos))
(verifica 69 (pontos jogo-com-spare-no-ultimo-frame))
(verifica 74 (pontos jogo-com-strike-no-primeiro-turn-do-ultimo-frame))
(verifica 240 (pontos jogo-quase-perfeito))
(verifica 300 (pontos jogo-perfeito))
