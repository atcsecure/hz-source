package nhz.http;

import nhz.Nhz;
import nhz.util.Convert;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static nhz.http.JSONResponses.INCORRECT_HEIGHT;

public final class GetBlockIds extends APIServlet.APIRequestHandler {

    static final GetBlockIds instance = new GetBlockIds();

    private GetBlockIds() {
        super(new APITag[] {APITag.BLOCKS}, "step");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {

        int step, height=0;
        try {
            String stepValue = Convert.emptyToNull(req.getParameter("step"));
            if (stepValue == null) {
                step=1;
            } else {
            	step = Integer.parseInt(stepValue);
            }
        } catch (RuntimeException e) {
            return INCORRECT_HEIGHT;
        }

        try {
        	int maxHeight=Nhz.getBlockchain().getLastBlock().getHeight();
            JSONArray blocks = new JSONArray();
            
            while (height<maxHeight-720) {
            	JSONObject block = new JSONObject();
            	block.put("height", height);
            	block.put("block", Convert.toUnsignedLong(Nhz.getBlockchain().getBlockIdAtHeight(height)));
            	blocks.add(block);
            	height+=step;
            }
            
            JSONObject response = new JSONObject();
            response.put("blocks", blocks);
            return response;
        } catch (RuntimeException e) {
            return INCORRECT_HEIGHT;
        }

    }

}