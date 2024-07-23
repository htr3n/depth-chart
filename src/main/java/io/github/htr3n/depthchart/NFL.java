package io.github.htr3n.depthchart;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NFL {

  private List<DepthChart> charts;
}
