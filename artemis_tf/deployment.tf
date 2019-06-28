resource "kubernetes_service" "artemis" {
  metadata {
    name = "artemis"

    labels {
      run = "artemis"
    }
  }

  spec {
    port {
      protocol    = "TCP"
      port        = 80
      target_port = "5000"
    }

    selector {
      run = "artemis"
    }

    type = "LoadBalancer"
  }
}

resource "kubernetes_deployment" "artemis" {
  metadata {
    name = "artemis"
  }

  spec {
    replicas = 1

    selector {
      match_labels {
        run = "artemis"
      }
    }

    template {
      metadata {
        labels {
          run = "artemis"
        }
      }

      spec {
        container {
          name  = "artemis"
          image = "50364747/artemis:v1"

          port {
            container_port = 80
          }
        }
      }
    }
  }
}
